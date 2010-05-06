package arcademis;

/**
 * This class contains a set of constants used in the arcademis framework to
 * qualificate components such as service handlers and call semantics, for
 * example.
 */
public interface ArcademisConstants {

	// These constants discriminate different kinds of service handlers

	/**
	 * The request sender is the service handler responsible for sending a request
	 * from the stub to the skeleton.
	 */
    public static final int REQUEST_SENDER = 1;

	/**
	 * The request receiver is the service handler responsible for receiving a
	 * call request from the stub and passing it to the skeleton.
	 */
    public static final int REQUEST_RECEIVER = 2;

	/**
	 * The response sender is responsible for sending to the stub the result of a
	 * remote call's processing.
	 */
    public static final int RESPONSE_SENDER = 3;

	/**
	 * The response receiver is the service handler responsible for receiving the
	 * result of generated by a remote call's processing.
	 */
    public static final int RESPONSE_RECEIVER = 4;

	/**
	 * This constant qualifies the service handler called
	 * <CODE>ChannelVerifier</CODE>. The main function of this service handler is
	 * to test if the channel can be used or if it is no longer available.
	 */
	public static final int CHANNEL_VERIFIER = 5;

	// These constants discriminate different kinds of semantics for call
	// processing.

	/**
	 * The great majority of applications use this kind of locator. The address is
	 * determined by a pair: host name and port number.
	 */
	public static final int HOST_PORT = 1;

	/**
	 * Shared memory locators use memory address in order to provide interprocess
	 * communication.
	 */
	public static final int SHARED_MEMORY = 2;

	/**
	 * This constant determines the lowest priority a remote method invocation can
	 * be given.
	 */
	public static final int HIGHEST_PRIORITY = 0;
}