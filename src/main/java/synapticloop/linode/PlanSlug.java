package synapticloop.linode;

// - - - - thoughtfully generated by synapticloop linode-api - - - - 
//     with the use of synapticloop templar templating language
//               (java-create-linodeplan-slug.templar)


public enum PlanSlug {
	LINODE_1024 (1l),
	LINODE_2048 (2l),
	LINODE_4096 (3l),
	LINODE_8192 (4l),
	LINODE_12288 (5l),
	LINODE_24576 (6l),
	LINODE_49152 (7l),
	LINODE_65536 (8l),
	LINODE_81920 (9l),
	LINODE_16384 (10l),
	LINODE_32768 (11l),
	LINODE_61440 (12l),
	LINODE_102400 (13l),
	LINODE_204800 (14l);

	private final Long planId;

	PlanSlug(Long planId) {
		this.planId = planId;
	}

	public Long planId() {
		return(planId);
	}
}
