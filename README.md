# SDG Android

> SDG는 [Shopl Android App](https://play.google.com/store/apps/details?id=me.planetory.io)을 위한 디자인 리소스와 재사용 가능한 컴포넌트, 가이드라인을 한데 모은 디자인 시스템입니다.

[![Maven Central](https://img.shields.io/maven-central/v/com.shoplworks/SDG-Android)](https://search.maven.org/artifact/com.shoplworks/SDG-Android)
[![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](https://github.com/shopl/shopl-design-guide-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/shopl/shopl-design-guide-android)

모든 구성요소는 [Jetpack Compose](https://developer.android.com/compose)로 이뤄져있습니다.

## Installation

SDG는 Maven Central을 통해 제공됩니다. 따라서 프로젝트에 SDG를 추가하기 전에 Maven Central이 Repository 리스트에 있는지 확인하세요.

```gradle
repositories {
    mavenCentral()
}
```
모듈 단위의 build.gradle에 다음 종속성을 추가하세요
```gradle
implementation "com.shoplworks:SDG-Android:sdgVersion"
```

## Features

* [Component](sdg/src/main/java/com/shopl/sdg/component): 일반 UI 요소(Text, Icon, Image 등)가 조합된 가장 작은 단위 (Button, Input 등)
* [Template](sdg/src/main/java/com/shopl/sdg/template): 하나 이상의 Component가 모여서 구성된 조합
* [Colors](sdg-common/src/main/java/com/shopl/sdg_common/foundation/SDGColor.kt): Shopl App 전반에서 사용되는 색상 팔레트
* [Spacing](sdg-common/src/main/java/com/shopl/sdg_common/foundation/spacing): 컴포넌트 간 간격, 패딩, 마진 등에 사용하는 간격 단위(예: 4dp, 8dp, 16dp)
* [Typography](sdg-common/src/main/java/com/shopl/sdg_common/foundation/typography):  제목, 본문, 캡션 등 텍스트 스타일을 위한 글꼴 크기, 줄 높이(line-height), 글자 굵기(font-weight), 폰트 패밀리 등
* [CornerRadius](sdg-common/src/main/java/com/shopl/sdg_common/foundation/SDGCornerRadius.kt): radius 값을 위한 기준 단위

## Module
SDG는 총 세개의 모듈로 구성되어 있습니다.

| 모듈           | 역할                                                      |
|--------------|---------------------------------------------------------|
| **sdg**        | • Component & Template 컴포저블 모음<br>                      |
| **sdg-common** | • 재사용 가능한 Composable 유틸 & 확장 함수<br>• 디자인 시스템 Foundation 정의<br> |
| **sdg-resource** | • 이미지, 폰트 등 디자인 리소스 전담<br>                      |

## License

SDG는 Apache 2.0 라이센스에 따라 제공됩니다. 자세한 내용은 라이센스 파일을 참조하세요.