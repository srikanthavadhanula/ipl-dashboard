import { React } from "react";

export const MatchDetailCard = ({match}) => {
  if(!match) return null;
  return (
    <div className="MatchDetailCard">
      <h1>Latest Match</h1>
      <h3>Match Details</h3>
      <p>{match.team1} ~~VS~~ {match.team2}</p>
    </div>
  );
}