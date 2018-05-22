
package annotations;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.MalformedURLException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate that the character sequence (e.g. string) is a valid URL.
 * 
 * @author Hardy Ferentschik
 */
public class ExtendedURLValidator implements ConstraintValidator<ExtendedURL, CharSequence> {

	private String	protocol;
	private String	host;
	private int		port;
	private boolean	admitData;


	@Override
	public void initialize(final ExtendedURL url) {
		this.protocol = url.protocol();
		this.host = url.host();
		this.port = url.port();
		this.admitData = url.admitData();
	}
	@Override
	public boolean isValid(final CharSequence value, final ConstraintValidatorContext constraintValidatorContext) {
		if (value == null || value.length() == 0)
			return true;
		if (this.admitData)
			if (value.toString().startsWith("data:"))
				return true;

		java.net.URL url;
		try {
			url = new java.net.URL(value.toString());
		} catch (final MalformedURLException e) {
			return false;
		}

		if (this.protocol != null && this.protocol.length() > 0 && !url.getProtocol().equals(this.protocol))
			return false;

		if (this.host != null && this.host.length() > 0 && !url.getHost().equals(this.host))
			return false;

		if (this.port != -1 && url.getPort() != this.port)
			return false;

		return true;
	}
}
