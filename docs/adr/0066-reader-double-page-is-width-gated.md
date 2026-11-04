# ADR 0066 — Reader double-page spread is width-gated, not orientation-gated

**Status:** Accepted

## Context

The reader's "Double page" preference used to take effect only when the window was in **physical landscape**. Android tested `Configuration.ORIENTATION_LANDSCAPE`; iOS tested `containerSize.width > containerSize.height`. Both checks answer "is the device turned sideways?", which stopped being the same question as "is there room for two readable pages?" once foldables shipped.

An unfolded Galaxy Fold is still in portrait orientation, but its inner display is wide enough for a spread. With the old rule the setting silently did nothing there, so a foldable read like a phone: one page at a time. This is the exact case ADR 0019 left to the phone layout, and the two decisions are compatible: the shell still renders the phone UI, while the reader uses the width it actually has.

## Decision

**The reader enables a spread whenever the window is at least 600 dp wide, the user has enabled the setting, and the mode is paginated.** Physical orientation no longer participates in the decision.

- `DOUBLE_PAGE_MIN_WIDTH_DP = 600f` is the shared breakpoint (`feature:reader`'s `DoublePageLayout.kt`), matching Material 3's Compact/Medium boundary. Each column therefore starts at a phone-page width.
- `shouldUseDoublePageSpread(requested, isPaginated, isDoublePageCapable)` is the single predicate both hosts evaluate; Android and iOS only supply the width signal.
- Android: `LocalWindowInfo.containerSize` converted to dp in `EpubReaderScreen`, threaded into `toEpubPreferences` / `toFragmentConfiguration` and `ReadiumPresenter.updateLayoutContext`. The fragment-recreation key includes the capability so unfolding repaginates live.
- iOS: the same conversion from `containerSize.width`; the preference-application effect keys on the capability so a fold/unfold re-submits `columnCount = 2`.
- The setting label changes from "Double page in landscape" to "Double page spread". The `ui_double_page_in_landscape` key becomes `ui_double_page_spread` in all three Compose-resource locales.

Because the setting is off by default and most users will never open Settings, the reader offers it **once on the first wide paginated window**:

- A single global flag (`ReaderPromptPreferencesStore.doublePagePromptShown`) is persisted per device (Android DataStore, iOS `NSUserDefaults`).
- The dialog explains that it can be changed later in Settings → Books → Display or from the reader's Aa panel.
- Either button records the flag, so the prompt never nags. Accepting writes a per-book override for the current screen bucket, mirroring what the Aa panel does.

## Alternatives considered

**Detect the hinge with `androidx.window` and split content into two panes.** Rejected for this change: it adds a dependency and two navigator views (or a custom centering layer) to solve a case the width gate already covers. On a symmetric foldable the hinge sits at the column gutter, so two-column pagination is visually one page per half. Hinge/posture handling becomes worthwhile only for offset hinges or tabletop posture, which is a separate layout feature.

**Keep the landscape gate and lower the threshold to include Medium width.** Rejected: a landscape phone would keep getting a spread while a portrait foldable would not, regardless of width. Orientation is the wrong axis.

**Prompt per screen bucket or per book.** Rejected: the prompt is about a device capability, not a book. A global once-only flag avoids repeating it for every book on the same device.

## Consequences

- Unfolded foldables and portrait tablets get a spread without rotating, provided the user opted in.
- Narrow split-screen windows stop getting a spread in landscape; each page would have been narrower than a phone page.
- The prompt is the only new persistent state. It is device-local and never synced.
