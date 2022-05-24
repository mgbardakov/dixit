import { CREATE_PLAYER } from "./types";

const initialState = {
    post: 1,
}


export const postReducer = (state = initialState, action: any) => {
    switch (action.type) {
        case CREATE_PLAYER:
            return {
                ...state,
                post: action.payload
            }
        default:
            return state;
    }
}