package synapticloop.linode;

public enum PlanSlug {
	LINODE_2048 (1l),
	LINODE_4096 (2l),
	LINODE_8192 (4l),
	LINODE_12288 (6l),
	LINODE_24576 (7l),
	LINODE_49152 (8l),
	LINODE_65536 (9l),
	LINODE_81920 (10l),
	LINODE_122880 (12l);

	private final Long planId;

	PlanSlug(Long planId) {
		this.planId = planId;
	}

	public Long planId() {
		return(planId);
	}
}
