package synapticloop.linode;

public enum DistributionSlug {
	ARCH_LINUX_2015_08 (142l),
	CENTOS_7 (129l),
	DEBIAN_7 (130l),
	DEBIAN_8_1 (140l),
	FEDORA_22 (141l),
	GENTOO_2014_12 (137l),
	OPENSUSE_13_2 (135l),
	SLACKWARE_14_1 (117l),
	UBUNTU_14_04_LTS (124l),
	UBUNTU_15_10 (144l),
	ARCH_LINUX_2015_02 (138l),
	CENTOS_5_6 (60l),
	CENTOS_6_5 (127l),
	DEBIAN_6 (78l),
	FEDORA_20 (122l),
	FEDORA_21 (134l),
	GENTOO_2013_11_26 (118l),
	OPENSUSE_13_1 (120l),
	SLACKWARE_13_37 (87l),
	UBUNTU_12_04_LTS (126l),
	UBUNTU_15_04 (139l),
	SLACKWARE_13_37_32BIT (86l);

	private final Long distributionId;

	DistributionSlug(Long distributionId) {
		this.distributionId = distributionId;
	}

	public Long distributionId() {
		return(distributionId);
	}
}
