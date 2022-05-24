import { CREATE_PLAYER } from "../reducers/types";
import { Dispatch } from 'redux';

// sync

export function createGame(game: any) {
    return {
        type: CREATE_PLAYER,
        payload: game
    }
}

//async
export function createPlayer() {

    return async (dispatch: Dispatch) => {
        const response = await fetch('https://jsonplaceholder.typicode.com/users')
        const data = await response.json()

        dispatch({
            type: CREATE_PLAYER,
            payload: data
        })

    }
}