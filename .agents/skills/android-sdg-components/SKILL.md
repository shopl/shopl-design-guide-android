---
name: android-sdg-components
description: Apply the supplied Android SDG UI conventions when implementing or updating Kotlin Compose components and sample screens in this repository.
---

# Android SDG 저장소 규칙

사용자가 제공한 UI Implementation Conventions를 저장소 Skill로 관리한다. 이전 로컬 전용 원본은 변경하지 않는다.

새 구현에는 아래 규칙을 적용한다. 기존 컴포넌트 수정에서는 현재 공개 API와 호출부를 먼저 확인한다. 규칙에 맞춘다는 이유만으로 기존 파라미터·이벤트·호환 API를 제거하지 않는다. 필요한 API 개편은 합의한 범위에서 수행한다.

문자열 리소스·허용 소스 경로·빌드/배포 버전·게시 제한은 상위 작업 지시를 따른다. 이 문서는 리소스 수정이나 commit/push/PR/빌드 실행 권한을 확대하지 않는다. AI 생성 중 문서는 읽기 전용이다.

# UI Implementation Conventions

This document outlines the coding conventions for UI components in the Shopl SDG project. These rules are derived from the "Golden Sample" (`SDGBoxButton`) and must be followed for all new component implementations.

## 1. Project Structure & Naming

### Directory Structure
Components are located in the `sdg` module, organized by component group and name.
```
sdg/src/main/java/com/shopl/sdg/component/
  ├── [group]/             # e.g., button, input, display
  │   └── [name]/          # e.g., box, capsule, ghost
  │       ├── SDG[Name].kt                     # Main Composable
  │       ├── SDG[Name]Type.kt                 # Sealed classes/Enums for variants
  │       └── SDG[Name]PreviewParameterProvider.kt  # Preview data provider
```

### Naming Rules
- **Component Name**: PascalCase, prefixed with `SDG` (e.g., `SDGBoxButton`).
- **File Name**: Matches the component name.
- **Package**: `com.shopl.sdg.component.[group].[name]`

## 2. Component Implementation

### Function Signature
- **Modifier**: Do **NOT** expose a generic `modifier: Modifier` as the first optional parameter.
  - Instead, provide specific parameters for layout control:
    - `marginValues: PaddingValues = PaddingValues()`
- **Parameters**:
  - **Figma-based Design**: Only expose parameters that are explicitly variable or configurable in the Figma design.
    - Do NOT add parameters for properties that are not shown as variable in Figma (e.g., `backgroundColor` if not specified in design).
    - This ensures the implementation matches the design intent exactly.
  - **Colors**: If a color is fixed in the design (e.g., "Neutral/700"), **hardcode it** in the component. Do **NOT** expose it as a parameter unless it is variable.
  - **Icons**: Use `iconResId` and `iconColor` naming convention.
  - **Lists**: Use `PersistentList` (from `kotlinx.collections.immutable`) for collection parameters.
  - **Complex States**: Group related parameters into a separate data class (e.g., `SDGIconHeaderRightItem`).

### Internal Structure
1.  **Layout & Width**:
    - If Figma shows a fixed width that approximates the screen width (e.g., 343px), interpret it as `fillMaxWidth()` with padding.
    - Use `Modifier.weight(1f, true)` for content that should take up remaining space.
2.  **Validation**:
    - Use `require()` blocks to enforce design constraints (e.g., max number of icons).
3.  **Conditional Modifiers**:
    - Use `.then(if (condition) Modifier... else Modifier)` pattern for conditional styling.
4.  **Code Organization Order**:
    - State declarations first (e.g., `rememberLazyListState()`, `remember { ... }`)
    - UI declaration (Layout composables)
    - Side effects last (e.g., `LaunchedEffect`, `DisposableEffect`)
5.  **Comments**:
    - Remove comments that merely describe what the code does if the code is self-explanatory.
    - Keep comments only for non-obvious business logic or technical decisions.

### Example
```kotlin
@Composable
fun SDGScrollTab(...) {
    // 1. State
    val listState = rememberLazyListState()

    // 2. UI
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = listState
    ) {
        // ...
    }

    // 3. Side Effects
    LaunchedEffect(selectedTabIndex) {
        listState.animateScrollToItem(selectedTabIndex)
    }
}
```

## 3. Previews

### Preview Parameter Provider
- Use `PreviewParameterProvider` to generate multiple preview states.
- Define a private/internal data class for parameters if needed.
- The preview function should be `private`.

### File Structure (`SDG[Name]PreviewParameterProvider.kt`)
```kotlin
internal class SDGBoxButtonPreviewParameterProvider : PreviewParameterProvider<SDGBoxButtonPreviewParameter> {
    override val values: Sequence<SDGBoxButtonPreviewParameter> = sequenceOf(...)
}

internal data class SDGBoxButtonPreviewParameter(
    val size: SDGBoxButtonSize,
    val type: SDGBoxButtonType,
    // ... all params needed for preview
)
```

### Preview Function
```kotlin
@Preview(showBackground = true)
@Composable
private fun PreviewSDGBoxButton(
    @PreviewParameter(SDGBoxButtonPreviewParameterProvider::class)
    parameter: SDGBoxButtonPreviewParameter
) {
    with(parameter) {
        SDGBoxButton(...)
    }
}
```

## 4. Documentation

### KDoc Structure
- **Components**:
  - Add KDoc to the main Composable function.
  - **Description**: Use the exact description text from the Figma design file (displayed under the component name).
  - Include a link to the Figma design using `@see`.
- **Utilities / Extensions**:
  - Write KDoc in **Korean (한국어)**.
  - explain the function's purpose and usage clearly.
  - Document all parameters using `@param`.

### Example (Component)
```kotlin
/**
 * SDG - Tab - Scroll Tab
 *
 * 제한없이 나열되는 탭 컴포넌트  // ← Figma에 명시된 설명 그대로 사용
 *
 * @see <a href="https://www.figma.com/design/...">Figma</a>
 */
```

### Example (Utility)
```kotlin
/**
 * 하단 테두리(Border)를 그립니다.
 *
 * @param strokeWidth 테두리 두께 (기본값: 1.dp)
 * @param color 테두리 색상
 */
fun Modifier.bottomBorder(...)
```

**Note**: Always check the Figma file for the official description text and copy it exactly.

## 5. Git Commit Conventions

### Commit Granularity
- **Atomic Commits**: Break down changes into small, logical units. Do not bundle multiple unrelated changes into a single commit.
  - Example: Commit the component implementation separately from the preview provider or type definitions if they are distinct steps.

### Commit Message Format
- **Language**: Korean (한국어).
- **Format**: Do NOT use prefixes like `feat:`, `fix:`. Just describe the change directly.
- **Content**: Clearly state *what* changed.

### Example
- **Bad**: `feat: Implement SDGScrollTab component` (Too broad, English, Prefix)
- **Good**:
  - `SDGScrollTab 컴포넌트 기본 구조 구현`
  - `SDGScrollTabPreviewParameterProvider 추가`
  - `SDGScrollTab 언더라인 레이아웃 수정`

## 6. Resource Usage

### Existing Resources First
- Before adding new resources or guessing resource names, **ALWAYS** check the existing modules:
  - `@[sdg-resource]`: Check for icons (`ic_common_*`, `ic_navi_*`, etc.) and basic assets.
  - `@[sdg-common]`: Check for foundation tokens like `SDGColor`, `SDGTypography`, `SDGSpacing`.
- **Do NOT use placeholders or guessed names** (e.g., `ic_common_search_s` if it doesn't exist). Find the correct mapped resource (e.g., `ic_common_search`).
- **Icons**:
  - Prefer using existing icons in `sdg-resource`.
  - Common prefixes: `ic_common_`, `ic_navi_`.
