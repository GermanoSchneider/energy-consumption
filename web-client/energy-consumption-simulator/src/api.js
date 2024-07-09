import axios from "axios";

const api = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {'Content-Type': 'application-json'}
});

export const findAllElectronics = async () => await api.get("electronics");

export const powerOn = async (id) => await api.get("power-on/" + id).catch((ex) => console.log(ex));

export const powerOff = async (id) => await api.get("power-off/" + id).catch((ex) => console.log(ex));

export const openSSE = async (id) => await api.get("open-sse/" + id).catch((ex) => console.log(ex));
