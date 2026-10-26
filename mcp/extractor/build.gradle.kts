plugins {
    application
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.20")
    implementation("com.google.code.gson:gson:2.11.0")
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
application {
    mainClass.set("com.shopl.sdg.mcp.ExtractCatalog")
}
