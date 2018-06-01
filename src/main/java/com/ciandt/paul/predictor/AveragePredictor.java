package com.ciandt.paul.predictor;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.HistoricalMatch;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;

/**
 * Average score predictor
 */
public class AveragePredictor implements Predictor {

	@Override
	public Prediction predict(Match match, Context context) {
		Prediction prediction = new Prediction(match);

		List<Integer> homeScores = new ArrayList<Integer>();
		List<Integer> homeDirectMatchScores = new ArrayList<Integer>();

		List<Integer> awayScores = new ArrayList<Integer>();
		List<Integer> awayDirectMatchScores = new ArrayList<Integer>();

		List<HistoricalMatch> historicalMatches = context.getHistoricalMatches();

		for (HistoricalMatch historicalMatch : historicalMatches) {

			if (match.getHomeTeam().equalsIgnoreCase(historicalMatch.getHomeTeam())
					&& match.getAwayTeam().equalsIgnoreCase(historicalMatch.getAwayTeam())) {
				homeDirectMatchScores.add(historicalMatch.getHomeScore());
				awayDirectMatchScores.add(historicalMatch.getAwayScore());
			} else if (match.getHomeTeam().equalsIgnoreCase(historicalMatch.getAwayTeam())
					&& match.getAwayTeam().equalsIgnoreCase(historicalMatch.getHomeTeam())) {
				homeDirectMatchScores.add(historicalMatch.getAwayScore());
				awayDirectMatchScores.add(historicalMatch.getHomeScore());
			}

			if (match.getHomeTeam().equalsIgnoreCase(historicalMatch.getHomeTeam())) {
				homeScores.add(historicalMatch.getHomeScore());
			} else if (match.getHomeTeam().equalsIgnoreCase(historicalMatch.getAwayTeam())) {
				homeScores.add(historicalMatch.getAwayScore());
			}

			if (match.getAwayTeam().equalsIgnoreCase(historicalMatch.getHomeTeam())) {
				awayScores.add(historicalMatch.getHomeScore());
			} else if (match.getAwayTeam().equalsIgnoreCase(historicalMatch.getAwayTeam())) {
				awayScores.add(historicalMatch.getAwayScore());
			}
		}

		int averageHomeScore = (int) Math.round(homeScores.stream().mapToInt(val -> val).average().orElse(0.0));
		int averageAwayScore = (int) Math.round(awayScores.stream().mapToInt(val -> val).average().orElse(0.0));
		int averageHomeDirectMatchScore = (int) Math
				.round(homeDirectMatchScores.stream().mapToInt(val -> val).average().orElse(0.0));
		int averageAwayDirectMatchScore = (int) Math
				.round(awayDirectMatchScores.stream().mapToInt(val -> val).average().orElse(0.0));

		prediction.setHomeScore((averageHomeScore + averageHomeDirectMatchScore) / 2);
		prediction.setAwayScore((averageAwayScore + averageAwayDirectMatchScore) / 2);

		return prediction;
	}
}
