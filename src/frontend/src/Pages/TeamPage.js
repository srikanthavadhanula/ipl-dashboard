import { React, useEffect, useState } from "react";
import { MatchDetailCard } from "../Components/MatchDetailCard";
import { MatchSmallCard } from "../Components/MatchSmallCard";
import { useParams } from 'react-router-dom';

export const TeamPage = () => {

  const [team, setTeam] = useState({
    teamMatches: []
  });

  const { teamName } = useParams();

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch(`http://localhost:8080/team/${teamName}`);
        const data = await response.json();
        console.log(data);
        setTeam(data);
      }

      fetchMatches();
    }, [teamName]
  )


  if(!team || !team.teamMatches){
    return <h2>Team not found</h2>
  }

  return (
    <div className="TeamPage">
        <h1>{team.teamName}</h1>
        <h3><MatchDetailCard name = {team.teamName} match = {team.teamMatches[0]}/></h3>
        <h4>Recent Matches</h4>
        {team.teamMatches.slice(1).map(match => <MatchSmallCard name = {team.teamName} match = {match}/>)}
    </div>
  );
}
