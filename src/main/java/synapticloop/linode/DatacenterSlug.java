package synapticloop.linode;

public enum DatacenterSlug {
	DALLAS_TX_USA (2l),
	FREMONT_CA_USA (3l),
	ATLANTA_GA_USA (4l),
	NEWARK_NJ_USA (6l),
	LONDON_ENGLAND_UK (7l),
	TOKYO_JP (8l),
	SINGAPORE_SG (9l),
	FRANKFURT_DE (10l);

	private final Long datacenterId;

	DatacenterSlug(Long datacenterId) {
		this.datacenterId = datacenterId;
	}

	public Long datacenterId() {
		return(datacenterId);
	}
}
