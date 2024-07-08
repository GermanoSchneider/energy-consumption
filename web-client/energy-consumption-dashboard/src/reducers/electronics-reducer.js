import { createSlice } from "@reduxjs/toolkit";

const electronicsReducer = createSlice({
    name: 'electronics',
    initialState: {
        electronics: []
    },
    reducers: {
        setElectronics: (state, action) => { state.electronics = action.payload }
    }
})

export const { setElectronics } = electronicsReducer.actions;
export default electronicsReducer.reducer;