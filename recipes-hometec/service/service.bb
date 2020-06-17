inherit systemd

SUMMARY = "Install and start a systemd service"
SECTION = "hometec"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#here we specify the source we want to build
SRC_URI = "\
            file://hometec.sh \
            file://hometec.service \
        "

S = "${WORKDIR}"

SYSTEMD_SERVICE_${PN} = "hometec.service"

#bitbake task
do_install() {
   install -d ${D}${bindir}
   install -m 0755 ${WORKDIR}/hometec.sh ${D}${bindir}
   install -d ${D}${systemd_unitdir}/system
   install -m 0644 ${WORKDIR}/hometec.service ${D}${systemd_unitdir}/system
}

#Pack the path
FILES_${PN} += "/usr/bin/"
FILES_${PN} += "/lib/systemd/system"

REQUIRED_DISTRO_FEATURES= "systemd"