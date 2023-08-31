export const createLocalDateTime = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = "00";
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
}

export const localDateTimeToCustomDateTime = (localDateTime) => {
    if (!localDateTime) {
        return '';
    }
    const date = new Date(localDateTime);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    const hour = date.getHours().toString().padStart(2, '0');
    const minute = date.getMinutes().toString().padStart(2, '0');
    return `${day}.${month}.${year} ${hour}:${minute}`;
}

export const customDateTimeToLocalDateTime = (customDateTime) => {
    if (!customDateTime) {
        return '';
    }
    const [date, time] = customDateTime.split(' ');
    const [day, month, year] = date.split('.');
    const [hour, minute] = time.split(':');

    const localDate = new Date(year, month - 1, day, hour, minute);

    return `${localDate.getFullYear()}-${(localDate.getMonth() + 1).toString().padStart(2, '0')}-${localDate.getDate().toString().padStart(2, '0')}T${localDate.getHours().toString().padStart(2, '0')}:${localDate.getMinutes().toString().padStart(2, '0')}:${localDate.getSeconds().toString().padStart(2, '0')}`;
}

export const calculateHash = async (txt) => {
    const msgBuffer = new TextEncoder().encode(txt);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(b => b.toString(16).padStart(2, "0")).join("");
}