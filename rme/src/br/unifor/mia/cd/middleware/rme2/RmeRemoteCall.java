package br.unifor.mia.cd.middleware.rme2;

import arcademis.*;
import arcademis.concreteComponents.*;

/**
 * This class contains all the parameters of a remote call and the return value
 * originated from its processing. All remote calls contains a reference for
 * the remote entity that is responsible for processing it. They also contains
 * a list of arguments and a reference for the return value generated from its
 * processing. Further parameters can be added in the extension of this class.
 */
public class RmeRemoteCall extends RemoteCall {

	private Identifier targetObjectIdentifier = null;

	private Stream arguments = null;

	/**
	 * The identifier of the call. The identifier is necessary to guarantee a
	 * given semantics to the method invocation. For instance, if necessary to
	 * implement an at most once semantics, then the call identifiers should be
	 * recorded in the server side, so that repeated calls can be recognized.
	 */
	private Identifier callId = null;

	/**
	 * The address to where the result of the method call should be sent, if
	 * there is a result.
	 */
	private Epid returnAddr = null;

	/**
	 * The code that identifies the operations encapsultated by this class.
	 * The code is necessary for the skeleton to choose the right operation
	 * from the available ones in the remote object implementation.
	 */
	private int opCode = 0;

	/**
	 * The empty constructor. It is necessary because all marshalable objects
	 * in Arcademis, needs one. The serialization protocol asks for a default
	 * empty constructor to read an object from a byte sequence.
	 */
	public RmeRemoteCall() {}

	/**
	 * Constructor method. It sets the call parameters and creates a unique
	 * identifier for the call.
	 * @param destiny the remote reference to the object that will process this
	 * call.
	 * @param arguments the arguments of the call.
	 */
	public RmeRemoteCall(Identifier targetObjectIdentifier, Stream arguments, int opCode) {
		this.targetObjectIdentifier = targetObjectIdentifier;
		this.arguments = arguments;
		this.opCode = opCode;
		this.callId = OrbAccessor.getIdentifier();
	}

	/**
	 * Informs the unique identifier of the remote object that is responsible by
	 * the processing of this call.
	 * @return an <CODE>Identifier</CODE> object that represents the unique
	 * identifier of the remote object responsible for handling this call.
	 */
	public Identifier getTargetObjectIdentifier() {
		return this.targetObjectIdentifier;
	}

	/**
	 * Returns a reference of the argument list of this call.
	 * @return an <CODE>Stream</CODE> object that encapsulates the arguments
	 * of this call.
	 */
	public Stream getArguments() {
		return this.arguments;
	}

	/**
	 * Informs the operation code of this method. Every method has an integer
	 * identifier that is assigned to it by the stub and skeleton
	 * implemenations.
	 * @return the integer value that defines the operation.
	 */
	public int getOperationCode() {
		return opCode;
	}

	/**
	 * Defines the return address of this call. The return address is the
	 * location to where any result generated by the call should be sent.
	 * @param returnAddr the new return address.
	 */
	public void setReturnAddress(Epid returnAddr) {
		this.returnAddr = returnAddr;
	}

	/**
	 * Informs the return address. The return address is the location to where
	 * any result generated by the call should be sent.
	 * @return the return address.
	 */
	public Epid getReturnAddress() {
		return returnAddr;
	}

	/**
	 * This method informs the identifier of the call. Such identifier is
	 * useful in the implementation of the call semantics.
	 * @param an object of the <CODE>Identifier</CODE> type.
	 */
	public Identifier getCallIdentifier() {
		return callId;
	}

	/**
	 * Gives textual information about this remote call.
	 * @return an <CODE>String</CODE> representing the internal state of this
	 * remote call.
	 */
	public String toString() {
		String rAddr = null;

		if(returnAddr == null)
			rAddr = "unavailable return address.";
		else
			rAddr = returnAddr.toString();

		return "Method request: " + callId.toString() +
			"\nTarget operation: " + opCode +
			"\nRemote destiny id: " + targetObjectIdentifier.toString() +
			"\nReturn address: " + rAddr;
	}

	/**
	 * Fills the stream b with the byte sequence that describes this object.
	 * @throws MarshalException if it is not possible to serialize this object.
	 * @param the stream used in the serialization process.
	 */
	public void marshal(Stream b) throws MarshalException {
		// send epid:
		b.write(this.returnAddr);
		// send call identifier:
		b.write(this.callId);
		// send target remote object identifier:
		b.write(this.targetObjectIdentifier);
		// send operation code
		b.write(this.opCode);
		// write the argument value:
		b.write(this.arguments);
	}

	/**
	 * Fills the content of this object with information retrived from a
	 * stream.
	 * @param the stream used in the serialization process.
	 */
	public void unmarshal(Stream b) throws MarshalException {
		// reading the end point identifier
		this.returnAddr = (HostPortEpid)b.readObject();

		// reading the identifier of the call
		this.callId = (Identifier)b.readObject();

		// reading the identifier of the target object
		this.targetObjectIdentifier = (Identifier)b.readObject();

		// reading the operation code
		this.opCode = b.readInt();

		// the remaining stream now just holds the call's arguments
		this.arguments = (Stream)b.readObject();
	}

}