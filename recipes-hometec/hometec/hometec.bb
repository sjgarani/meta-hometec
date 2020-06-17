SUMMARY = "State Machine of Hometec"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "boost"

SRC_URI = "\
            file://CMakeLists.txt \
            file://main.cpp \
            file://src/statemachine.cpp \
            file://include/statemachine.hpp \
        "

S = "${WORKDIR}"

inherit pkgconfig cmake

EXTRA_OECMAKE = ""

do_install() {
    install -d ${D}${libexecdir}
    install -m 0755 hometec ${D}${libexecdir}
}