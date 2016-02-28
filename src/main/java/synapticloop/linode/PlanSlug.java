package synapticloop.linode;

public enum PlanSlug {
	LINODE_1024 (1l),
	LINODE_2048 (2l),
	LINODE_4096 (4l),
	LINODE_8192 (6l),
	LINODE_16384 (7l),
	LINODE_32768 (8l),
	LINODE_49152 (9l),
	LINODE_65536 (10l),
	LINODE_98304 (12l);

	private final Long planId;

	PlanSlug(Long planId) {
		this.planId = planId;
	}

	public Long planId() {
		return(planId);
	}
}
