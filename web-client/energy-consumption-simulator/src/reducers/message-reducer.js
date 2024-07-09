import { createSlice } from "@reduxjs/toolkit";

const messageReducer = createSlice({
    name: 'messages',
    initialState: {
        messages: [],
    },
    reducers: {
        setMessages: (state, action) => { 
            state.messages = state.messages.filter((message) => message.id !== action.payload.id);
            state.messages.push(action.payload);
         },
    }
})

export const { setMessages } = messageReducer.actions;
export default messageReducer.reducer;