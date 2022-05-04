import React, { useEffect, useRef, useState } from "react"
import './Dixit.scss'
import Deck from "../../components/Deck/Deck"
import { players } from "../../data/players"
import Player from "../../components/Player/Player"
import { PlayerType } from "../../types/types"
import { Table } from "../../components/Table/Table"
import AuthModal from "../../components/AuthModal/AuthModal"
import {
    useParams
} from "react-router-dom";

// const ws = new WebSocket('ws://localhost:3335')
export const DixitPage: React.FC = () => {
    return (<></>)
}
export const Dixit: React.FC = () => {
    let { gameID } = useParams();
    const [status, setStatus] = useState('offline')
    const [messages, setMessages] = useState([])
    console.log(gameID)
    const input = useRef()


    useEffect(() => {
        // ws.onopen = () => {
        //     setStatus('online')
        // }

        // ws.onclose = () => {
        //     setStatus('offline')
        // }

        // ws.onmessage = response => {
        //     const messageCurrent = JSON.parse(response.data)
        //     console.log(messageCurrent)
        //     setMessages(prev => [messageCurrent.mess, ...prev])
        // }
    }, [])



    const send = () => {

        //   ws.send(JSON.stringify({ mess: input.current.value }))
        //   input.current.value = ''
    }

    return (
        <div className="dixit__wrapper">
            <div className="dixit">
                {players.map((player, i) => <Player key={i} player={player} num={i} />)}
                <Table />
                <Deck />
            </div>
        </div>
    )

}

