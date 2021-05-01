import { React, useEffect, useState } from "react";
import { MatchDetailCard } from "../Components/MatchDetailCard";
import { MatchSmallCard } from "../Components/MatchSmallCard";

export const TeamPage = () => {

  const [team, setTeam] = useState({
    teamMatches: []
  });

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch("http://localhost:8080/team/Rajasthan Royals");
        const data = await response.json();
        console.log(data);
        setTeam(data);
      }

      fetchMatches();
    }, []
  )

  return (
    <div className="TeamPage">
        <h1>{team.teamName}</h1>
        <h3><MatchDetailCard match = {team.teamMatches[0]}/></h3>
        <h4>Recent Matches</h4>
        {team.teamMatches.slice(1).map(match => <MatchSmallCard match = {match}/>)}
    </div>
  );
}
