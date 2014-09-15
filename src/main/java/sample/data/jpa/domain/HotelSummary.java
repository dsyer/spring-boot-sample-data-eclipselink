/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.jpa.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class HotelSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final MathContext MATH_CONTEXT = new MathContext(2,
			RoundingMode.HALF_UP);

	private final City city;

	private final String name;

	private final Double averageRating;

	private final Integer averageRatingRounded;

	public HotelSummary(City city, String name, Double averageRating) {
		this.city = city;
		this.name = name;
		this.averageRating = averageRating == null ? null : new BigDecimal(averageRating,
				MATH_CONTEXT).doubleValue();
		this.averageRatingRounded = averageRating == null ? null : (int) Math
				.round(averageRating);
	}

	public City getCity() {
		return this.city;
	}

	public String getName() {
		return this.name;
	}

	public Double getAverageRating() {
		return this.averageRating;
	}

	public Integer getAverageRatingRounded() {
		return this.averageRatingRounded;
	}
}
