SUMMARY = "State Machine of Hometec"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

DEPENDS += "erlang"
RDEPENDS_${PN} = "erlang"

SRCREV = "${AUTOREV}"
BPV = "0.1.0"
PV = "${BPV}+gitr${SRCPV}"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/sjgarani/hometec.git;branch=dev_erl;protocol=git"

SRC_DIR = "${libdir}/erlang/lib/${P}/src"
EBIN_DIR = "${libdir}/erlang/lib/${P}/ebin"
CONF_DIR = "${sysconfdir}/hometec.d"

# The following is a workaround to prevent AC_ERLANG_NEED_ERL autoconf macro
# failing due running erl program compiled for arm running on x86_64
EXTRA_OECONF = "--host=x86_64-linux --target=x86_64-linux"

INITSCRIPT_NAME = "hometec.otp.system"
INITSCRIPT_PARAMS = "defaults 10"

inherit autotools update-rc.d

do_install_append() {
    cd ${S}/sys; /usr/bin/erl -noshell -pa ${S}/src -s systools make_script hometec -s erlang halt
    install -d ${D}${SRC_DIR}
    install -m 0755 ${S}/src/hometec.app.src ${D}${SRC_DIR}
    install -m 0755 ${S}/src/*.erl ${D}${SRC_DIR}
    install -d ${D}${EBIN_DIR}
    install -m 0755 ${B}/src/hometec.app ${D}${EBIN_DIR}
    install -m 0755 ${B}/src/*.beam ${D}${EBIN_DIR}
    install -d ${D}${CONF_DIR}
    install -m 0755 ${S}/sys/hometec.config ${D}${CONF_DIR}
    install -m 0755 ${B}/sys/hometec.boot ${D}${CONF_DIR}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/sys/hometec.otp.system ${D}${sysconfdir}/init.d
    install -d ${D}${bindir}
    install -m 0755 ${B}/sys/hometec.start ${D}${bindir}
    install -m 0755 ${B}/sys/hometec.stop ${D}${bindir}
}

FILES_${PN}  = "${EBIN_DIR}/hometec.app"
FILES_${PN} += "${EBIN_DIR}/hometec_app.beam"
FILES_${PN} += "${EBIN_DIR}/hometec_sup.beam"
FILES_${PN} += "${EBIN_DIR}/hometec.beam"
FILES_${PN} += "${CONF_DIR}/hometec.config"
FILES_${PN} += "${CONF_DIR}/hometec.boot"
FILES_${PN} += "${sysconfdir}/init.d/hometec.otp.system"
FILES_${PN} += "${bindir}/hometec.start"
FILES_${PN} += "${bindir}/hometec.stop"

FILES_${PN}-dev  = "${SRC_DIR}/hometec.app.src"
FILES_${PN}-dev += "${SRC_DIR}/hometec_app.erl"
FILES_${PN}-dev += "${SRC_DIR}/hometec_sup.erl"
FILES_${PN}-dev += "${SRC_DIR}/hometec.erl"