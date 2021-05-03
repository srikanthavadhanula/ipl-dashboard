import { React } from "react";
import { Link } from 'react-router-dom';

export const MatchSmallCard = ({name, match}) => {
  const otherTeam = match.team1 === name ? match.team2 : match.team1;
  const otherTeamRoute = `/team/${otherTeam}`;
  return (
    <div className="MatchSmallCard">
      <h4>VS <Link to={otherTeamRoute}>{otherTeam}</Link>  </h4>
      <p>{match.matchWinner} won by {match.resultMargin} {match.result}</p>
    </div>
  );
}
