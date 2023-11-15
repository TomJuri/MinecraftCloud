plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "HyCloud"

include(":api")
include(":common")
include(":controller")
include(":service-wrapper")
include(":wrapper")
