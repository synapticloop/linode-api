package synapticloop.linode;

public enum DistributionSlug {
	ARCH_2016_09_03 (148l),
	CENTOS_7 (129l),
	DEBIAN_7 (130l),
	DEBIAN_8 (140l),
	FEDORA_25 (155l),
	GENTOO_2016_10_13 (153l),
	OPENSUSE_LEAP_42_2 (154l),
	SLACKWARE_14_2 (151l),
	UBUNTU_16_04_LTS (146l),
	UBUNTU_16_10 (152l),
	CENTOS_5_6 (60l),
	CENTOS_6_5 (127l),
	FEDORA_23 (147l),
	FEDORA_24 (149l),
	OPENSUSE_13_2 (135l),
	OPENSUSE_LEAP_42_1 (150l),
	SLACKWARE_13_37 (87l),
	SLACKWARE_14_1 (117l),
	UBUNTU_12_04_LTS (126l),
	UBUNTU_14_04_LTS (124l),
	SLACKWARE_13_37_32BIT (86l);

	private final Long distributionId;

	DistributionSlug(Long distributionId) {
		this.distributionId = distributionId;
	}

	public Long distributionId() {
		return(distributionId);
	}
}
