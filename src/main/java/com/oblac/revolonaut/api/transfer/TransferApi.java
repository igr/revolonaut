package com.oblac.revolonaut.api.transfer;

import com.oblac.revolonaut.Cmd;
import com.oblac.revolonaut.api.ExceptionToHttpResponseMapper;
import com.oblac.revolonaut.cmd.TransferCmd;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller("/transfer")
public class TransferApi implements ExceptionToHttpResponseMapper {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReqBody {
		String ibanFrom;
		String ibanTo;
		String amount;
	}

	@Post
	@Produces(MediaType.TEXT_JSON)
	public HttpResponse post(@Body final ReqBody in) {
		return runAndMapExToStatus(() ->
			Cmd.of(TransferCmd.class)
				.wrap(it -> it.transfer(TransferCmd.Input.builder()
					.ibanFrom(in.getIbanFrom())
					.ibanTo(in.getIbanTo())
					.amount(in.getAmount())
					.build()
				))
				.run()
		);
	}

}
