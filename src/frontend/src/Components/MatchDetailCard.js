import { React } from "react";
import { Link } from 'react-router-dom';

export const MatchDetailCard = ({name, match}) => {
  if(!match) return null;
  const otherTeam = match.team1 === name ? match.team2 : match.team1;
  const otherTeamRoute = `/team/${otherTeam}`;
  return (
    <div className="MatchDetailCard">
      <h3>Latest Match</h3>
      <h1>VS <Link to={otherTeamRoute}>{otherTeam}</Link></h1>
      <h2>Date : {match.date}</h2>
      <h3>Venue : {match.venue}</h3>
      <h3>{match.matchWinner} won by {match.resultMargin} {match.result} </h3>
    </div>
  );
}
