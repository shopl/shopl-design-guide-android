---
name: sdg-android-foundation
description: SDG를 사용하는 Android Compose UI에서 색상·타이포그래피·간격·radius·아이콘을 적용하거나 기존 원시 값을 디자인 시스템 참조로 바꿀 때 사용한다.
---

# Android SDG 토큰과 아이콘

- `sdg_search_assets`의 kind(color, typography, spacing, radius, icon)와 검색어로 실제 선언을 조회한다. assetId로 정확한 항목을 다시 조회할 수 있다.
- 응답의 Kotlin reference와 import를 사용한다. 이름이 비슷하다는 이유로 토큰·drawable·스타일을 만들어 쓰지 않는다. 값은 소스 표현식일 수 있으므로 계산된 색상·크기로 단정하지 않는다.
- component·template·기존 kit에서 이미 제공하는 스타일을 먼저 사용한다. 디자인에 맞는 토큰이 없으면 임의로 가장 가까운 값으로 바꾸지 말고 차이를 보고한다.
- 아이콘의 drawable 참조와 필요한 모듈을 확인한다. 이미지가 원래 색을 보존해야 하는지, tint가 필요한지는 기존 사용 예와 디자인을 기준으로 판단한다.
- 소비 앱이 이전 SDG 버전이면 최신 의존성과 실제 해석을 먼저 맞춘다. 앱 리소스 변경과 문자열 연결에는 저장소 정책을 적용한다.
