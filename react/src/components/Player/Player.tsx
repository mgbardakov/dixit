import { PlayerType } from "../../types/types"
import './Player.scss'

type PlayerProps = {
    player: PlayerType
    num: number
}

const Player: React.FC<PlayerProps> = ({ player, num }: PlayerProps) => {
    const color = { color: player.color }
    return (
        <div className={`player player_${num}`}>
            <p className="player__name" style={color}>{player.name}</p>
        </div>
    )
}

export default Player