---
name: sdg-builder-rule
description: 이 저장소의 Kotlin Compose 컴포넌트·샘플 화면 구현과 관련 브랜치·PR 작업에 Android SDG 규칙을 적용한다.
---

# Android SDG 저장소 규칙

사용자가 제공한 UI 구현 규칙을 저장소 Skill로 관리한다. 이전 로컬 전용 원본은 변경하지 않는다.

새 구현에는 아래 규칙을 적용한다. 기존 컴포넌트 수정에서는 현재 공개 API와 호출부를 먼저 확인한다. 규칙에 맞춘다는 이유만으로 기존 파라미터·이벤트·호환 API를 제거하지 않는다. 필요한 API 개편은 합의한 범위에서 수행한다.

문자열 리소스·허용 소스 경로·빌드/배포 버전·게시 제한은 상위 작업 지시를 따른다. 이 문서는 리소스 수정이나 commit/push/PR/빌드 실행 권한을 확대하지 않는다. AI 생성 중 문서는 읽기 전용이다.

# UI 구현 규칙

Shopl SDG 프로젝트의 UI 컴포넌트 코드 규칙이다. 기준 구현인 `SDGBoxButton`에서 도출했으며, 새 컴포넌트를 구현할 때 적용한다.

## 1. 프로젝트 구조와 이름

### 디렉터리 구조
컴포넌트는 `sdg` 모듈 안에서 컴포넌트 그룹과 이름으로 나눈다.
```
sdg/src/main/java/com/shopl/sdg/component/
  ├── [group]/             # 예: button, input, display
  │   └── [name]/          # 예: box, capsule, ghost
  │       ├── SDG[Name].kt                     # 주 Composable
  │       ├── SDG[Name]Type.kt                 # 변형을 정의하는 sealed 클래스·enum
  │       ├── SDG[Name]PreviewParameter.kt     # 미리보기 파라미터 데이터
  │       └── SDG[Name]PreviewParameterProvider.kt  # 미리보기 데이터 제공자
```

### 이름 규칙
- **컴포넌트 이름**: PascalCase를 사용하고 `SDG` 접두사를 붙인다. 예: `SDGBoxButton`.
- **파일 이름**: 컴포넌트 이름과 일치시킨다.
- **패키지**: `com.shopl.sdg.component.[group].[name]`

## 2. 컴포넌트 구현

### 함수 선언
- **Modifier**: 일반적인 `modifier: Modifier`를 첫 번째 선택 파라미터로 노출하지 않는다.
  - 대신 레이아웃을 제어할 구체적인 파라미터를 제공한다.
    - `marginValues: PaddingValues = PaddingValues()`
- **파라미터**:
  - **Figma 디자인 기준**: Figma에서 변형하거나 설정할 수 있다고 명시한 항목만 파라미터로 노출한다.
    - Figma에서 가변 속성으로 표시하지 않은 항목은 파라미터로 추가하지 않는다. 예: 디자인에 명시되지 않은 `backgroundColor`.
    - 구현을 디자인 의도와 일치시키기 위한 기준이다.
  - **색상**: 디자인에서 고정한 색상(예: "Neutral/700")은 컴포넌트 내부에 고정값으로 지정한다. 가변 속성일 때만 파라미터로 노출한다.
  - **아이콘**: `iconResId`와 `iconColor` 이름을 사용한다.
  - **목록**: 컬렉션 파라미터에는 `kotlinx.collections.immutable`의 `PersistentList`를 사용한다.
  - **복합 상태**: 관련 파라미터를 별도 데이터 클래스로 묶는다. 예: `SDGIconHeaderRightItem`.

### 내부 구조
1. **레이아웃과 너비**:
   - Figma의 고정 너비가 화면 너비에 가까우면(예: 343px), 여백을 둔 `fillMaxWidth()`로 해석한다.
   - 남은 공간을 차지해야 하는 콘텐츠에는 `Modifier.weight(1f, true)`를 사용한다.
2. **유효성 검사**:
   - 최대 아이콘 개수 같은 디자인 제약은 `require()`로 검사한다.
3. **조건부 Modifier**:
   - 조건부 스타일은 `.then(if (condition) Modifier... else Modifier)` 패턴을 사용한다.
4. **코드 배치 순서**:
   - 상태 선언을 먼저 둔다. 예: `rememberLazyListState()`, `remember { ... }`
   - 다음으로 UI를 선언한다. 예: 레이아웃 Composable
   - 부수 효과를 마지막에 둔다. 예: `LaunchedEffect`, `DisposableEffect`
5. **주석**:
   - 코드 자체로 명확한 동작을 반복 설명하는 주석은 제거한다.
   - 코드만으로 알기 어려운 업무 로직이나 기술적 판단을 설명하는 주석을 남긴다.

### 예시
```kotlin
@Composable
fun SDGScrollTab(...) {
    // 1. 상태
    val listState = rememberLazyListState()

    // 2. UI
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = listState
    ) {
        // ...
    }

    // 3. 부수 효과
    LaunchedEffect(selectedTabIndex) {
        listState.animateScrollToItem(selectedTabIndex)
    }
}
```

## 3. 미리보기

### 미리보기 파라미터 제공자
- `PreviewParameterProvider`로 여러 미리보기 상태를 제공한다.
- 제공자 내부에서 미리보기 상태를 만드는 `private` 팩토리 메서드는 한글로 상태·크기·유형을 설명하는 이름을 사용한다. 단어는 `_`로 구분한다. 예: `활성_Medium_Solid()`, `비활성_Medium_Solid()`. 이 규칙은 공개 API나 일반 구현 함수의 이름을 한글로 바꾸라는 의미가 아니다.
- `PreviewParameter` 데이터 클래스와 `PreviewParameterProvider` 클래스는 각각 `SDG[Name]PreviewParameter.kt`와 `SDG[Name]PreviewParameterProvider.kt` 파일로 분리한다. 두 선언을 한 파일에 함께 두지 않는다.
- 서로 다른 파일에서 참조하는 파라미터 타입은 `internal` 등 참조 가능한 접근 수준으로 선언한다.
- 미리보기 함수는 `private`로 선언한다.

### 파라미터 파일 (`SDG[Name]PreviewParameter.kt`)
```kotlin
internal data class SDGBoxButtonPreviewParameter(
    val size: SDGBoxButtonSize,
    val type: SDGBoxButtonType,
    // ... 미리보기에 필요한 나머지 파라미터
)
```

### 제공자 파일 (`SDG[Name]PreviewParameterProvider.kt`)
```kotlin
internal class SDGBoxButtonPreviewParameterProvider : PreviewParameterProvider<SDGBoxButtonPreviewParameter> {
    override val values: Sequence<SDGBoxButtonPreviewParameter> = sequenceOf(
        활성_Medium_Solid(),
        비활성_Medium_Solid(),
    )

    private fun 활성_Medium_Solid() = SDGBoxButtonPreviewParameter(...)
    private fun 비활성_Medium_Solid() = SDGBoxButtonPreviewParameter(...)
}
```

### 미리보기 함수
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

## 4. 문서 주석

### KDoc 구조
- **컴포넌트**:
  - 주 Composable 함수에 KDoc을 추가한다.
  - **설명**: Figma 디자인 파일의 컴포넌트 이름 아래에 표시된 설명을 그대로 사용한다.
  - `@see`로 Figma 디자인 링크를 포함한다.
  - `@version`으로 해당 컴포넌트에 적용한 디자인 가이드 버전을 명시한다. Figma 문서와 합의된 작업 요구사항에서 정확한 버전을 확인한다. 코드 수정만으로 임의 증가시키지 않으며, 앱·라이브러리 배포 버전과 구분한다.
- **유틸리티·확장 함수**:
  - KDoc은 한국어로 작성한다.
  - 함수의 목적과 사용법을 명확하게 설명한다.
  - 모든 파라미터를 `@param`으로 설명한다.

### 컴포넌트 예시
```kotlin
/**
 * SDG - Tab - Scroll Tab
 *
 * 제한없이 나열되는 탭 컴포넌트  // ← Figma에 명시된 설명 그대로 사용
 *
 * @version 2.3.48
 *
 * @see <a href="https://www.figma.com/design/...">Figma</a>
 */
```

### 유틸리티 예시
```kotlin
/**
 * 하단 테두리(Border)를 그립니다.
 *
 * @param strokeWidth 테두리 두께 (기본값: 1.dp)
 * @param color 테두리 색상
 */
fun Modifier.bottomBorder(...)
```

**유의사항**: Figma 파일의 공식 설명을 항상 확인하고 그대로 사용한다.

## 5. Git 커밋 규칙

### 커밋 단위
- **논리적으로 분리된 커밋**: 변경을 작은 논리 단위로 나눈다. 관련 없는 변경 여러 개를 한 커밋으로 묶지 않는다.
  - 예: 컴포넌트 구현과 미리보기 제공자·타입 정의가 별도 작업이면 커밋도 나눈다.

### 커밋 메시지 형식
- **언어**: 한국어.
- **형식**: `feat:`, `fix:` 같은 접두사 없이 변경 내용을 바로 적는다.
- **내용**: 무엇을 바꿨는지 명확하게 적는다.

### 예시
- **나쁜 예시**: `feat: Implement SDGScrollTab component` (범위가 넓고, 영어·접두사를 사용함)
- **좋은 예시**:
  - `SDGScrollTab 컴포넌트 기본 구조 구현`
  - `SDGScrollTabPreviewParameterProvider 추가`
  - `SDGScrollTab 언더라인 레이아웃 수정`

## 6. 리소스 사용

### 기존 리소스 우선 확인
- 새 리소스를 추가하거나 이름을 추측하기 전에 다음 모듈을 항상 확인한다.
  - `@[sdg-resource]`: 아이콘(`ic_common_*`, `ic_navi_*` 등)과 기본 에셋을 확인한다.
  - `@[sdg-common]`: `SDGColor`, `SDGTypography`, `SDGSpacing` 같은 기본 토큰을 확인한다.
- **임시 이름이나 추측한 이름을 사용하지 않는다.** 예를 들어 `ic_common_search_s`가 없다면 사용하지 말고, 실제 매핑된 `ic_common_search` 같은 리소스를 찾는다.
- **아이콘**:
  - `sdg-resource`의 기존 아이콘을 우선 사용한다.
  - 일반적인 접두사는 `ic_common_`, `ic_navi_`이다.

## 7. 브랜치·PR·배포

### 작업 브랜치

- 작업 브랜치는 `작업 목적/작업자/이슈번호-이슈요약` 형식을 따른다. 이슈 요약은 생략할 수 있다. 예: `update/scott/SH-11377-fix-disabled-status-white-label`.
- 목적은 새 디자인 요소 추가 `add`, 기존 디자인 요소·기타 수정 `update`, 주석·문서 변경 `document`로 구분한다. Git의 파일 추가 여부만으로 목적을 확정하지 않는다.
- 작업자 표기와 Jira 이슈는 확인한 작업 정보를 사용한다. 작업자를 GitHub 로그인과 같다고 가정하거나 이슈번호를 임의로 만들지 않는다. 필요한 정보가 없으면 확인하고, UUID로 팀 브랜치 규칙을 대체하지 않는다.
- 같은 작업의 재빌드·리뷰 수정은 기존 브랜치·PR을 이어간다. 이름만으로 다른 사람의 PR을 수정하지 않으며 저장소·작업 기록·최신 커밋을 확인한다. 기존 브랜치의 이름 변경이나 main 병합은 별도 합의 없이 수행하지 않는다.
- 연결 프로그램이 관리하는 브랜치는 AI가 임의로 바꾸지 않는다. 기존 `add|update|automation/figma-UUID` 기록은 이관 호환 대상이며 팀의 표준 작업 브랜치 형식은 아니다.

### PR·리뷰

- 작업 목적에 맞는 `add`, `update`, `document` 라벨을 확인한다. `version`은 배포 버전 업데이트 목적이다. 브랜치·Jira 정보로 제목과 라벨을 붙이는 기존 저장소 자동화와 충돌하지 않게 한다.
- main에 병합하는 모든 변경은 최소 1명의 리뷰어 approve를 받는다. AI의 자체 검사나 빌드 성공을 이 승인으로 대체하지 않는다. 실제 병합 권한은 별도 사용자 지시를 따른다.
- 문서 변경의 목적이 `document`라고 해서 이번 작업에 허용되지 않은 파일이나 문자열 리소스를 수정할 수 있는 것은 아니다.

### 배포 브랜치·버전

- 배포 브랜치는 작업 브랜치와 구분한다. 문서 예시: `version/scott/minor-1.2.0`. 배포 규모는 `major`, `minor`, `patch`이며 작업 목적 `update`와 혼용하지 않는다.
- 호환성에 영향을 주는 변경은 Major, 새 컴포넌트 추가는 Minor, 버그·세부 스타일·문서 수정 등은 Patch 기준으로 검토한다. API 호환성·미이관 요소·리팩터링 범위가 불명확하면 담당자와 확인한다.
- Confluence 두 문서의 미이관 요소 분류와 `update`·`patch` 표현에는 해석이 필요한 부분이 있다. 이를 근거로 작업 목적과 배포 규모를 임의로 매핑하지 않는다.
- 현재 버전 집계 코드는 `add` 라벨 유무로 minor/patch를 정하므로 작업 라벨 누락을 확인한다. major 자동 생성까지 지원한다고 가정하지 않는다.
- `major`, `minor`, `patch` 라벨 PR의 main 병합은 실제 Maven 배포를 실행할 수 있다. `version` 라벨 하나와 동일한 의미로 취급하지 않으며, 별도 승인 없이 릴리스용 라벨·배포 버전을 변경하거나 배포를 실행하지 않는다.
- 컴포넌트의 디자인 `@version`과 PublishingConfig의 라이브러리 배포 버전을 구분한다.

### 규칙과 자동 검사의 역할

- 이 Skill은 작업 목적·리뷰·배포 판단의 기준이다. 원격 validator는 입력 형식·커밋 일치·허용 파일 범위를 검사한다. validator 통과가 디자인 일치·코드 품질·리뷰 승인·러너 사용 권한을 보장하지 않는다.
- 규칙 변경은 팀 리뷰를 거친다. 코드스타일을 검사기에 추가하려면 실제 유즈케이스와 오탐 영향을 먼저 확인한다.

근거: [SDG 개발 컨벤션](https://shoplworks.atlassian.net/wiki/spaces/android/pages/1111392279/SDG), [SDG 배포 방법](https://shoplworks.atlassian.net/wiki/spaces/android/pages/1111392259/SDG)의 2026-10-19 게시 본문과 현재 저장소 PR·버전 자동화. 미게시 초안은 기준으로 사용하지 않았다.
