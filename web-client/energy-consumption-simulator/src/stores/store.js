import { configureStore } from "@reduxjs/toolkit";
import electronicsReducer from "../reducers/electronics-reducer";
import messageReducer from "../reducers/message-reducer";

const store = configureStore({
    reducer: {
        messages: messageReducer,
        electronics: electronicsReducer
    }
})

export default store;