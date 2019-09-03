package com.oblac.revolonaut.api.accounts.fixture;

import com.oblac.revolonaut.api.transfer.TransferApi;

public class TransferApiInputs {

	public final static TransferApi.ReqBody tr0101_0202_12_34 = TransferApi.ReqBody
		.builder()
		.ibanFrom("RE0101")
		.ibanTo("RE0202")
		.amount("12.34")
		.build();

	public final static TransferApi.ReqBody tr0101_0202_1000 = TransferApi.ReqBody
		.builder()
		.ibanFrom("RE0101")
		.ibanTo("RE0202")
		.amount("1000")
		.build();

	public final static TransferApi.ReqBody tr0101_0303_10 = TransferApi.ReqBody
		.builder()
		.ibanFrom("RE0101")
		.ibanTo("RE0303")
		.amount("10")
		.build();

	public final static TransferApi.ReqBody tr0101_0202_NEGATIVE = TransferApi.ReqBody
		.builder()
		.ibanFrom("RE0101")
		.ibanTo("RE0202")
		.amount("-10")
		.build();

	public final static TransferApi.ReqBody tr0101_0202_NAN = TransferApi.ReqBody
		.builder()
		.ibanFrom("RE0101")
		.ibanTo("RE0202")
		.amount("nothing")
		.build();

}
