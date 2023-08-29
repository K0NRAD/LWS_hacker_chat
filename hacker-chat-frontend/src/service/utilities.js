export const createISOTime = () => {
    const now = new Date();
    now.setSeconds(0);
    now.setMilliseconds(0);
    return now.toISOString();
}

export const toIsoString = (dateTime) => {
    const [date, time] = dateTime.split(' ');
    const [day, month, year] = date.split('.');
    const [hour, minute] = time.split(':');
    return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}T${hour}:${minute}:00`;
}

export function isoToCustom(isoString) {
    if(!isoString) {
        return '';
    }
    const date = new Date(isoString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    const hour = date.getHours().toString().padStart(2, '0');
    const minute = date.getMinutes().toString().padStart(2, '0');
    return `${day}.${month}.${year} ${hour}:${minute}`;
}

export function customToIso(customString) {
    if(!customString) {
        return '';
    }
    const [date, time] = customString.split(' ');
    const [day, month, year] = date.split('.');
    const [hour, minute] = time.split(':');

    const localDate = new Date(year, month - 1, day, hour, minute);

    return `${localDate.getFullYear()}-${(localDate.getMonth() + 1).toString().padStart(2, '0')}-${localDate.getDate().toString().padStart(2, '0')}T${localDate.getHours().toString().padStart(2, '0')}:${localDate.getMinutes().toString().padStart(2, '0')}:${localDate.getSeconds().toString().padStart(2, '0')}`;
}