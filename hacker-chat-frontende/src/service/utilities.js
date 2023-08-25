export const createISOTime = () => {
    const now = new Date();
    now.setSeconds(0);
    now.setMilliseconds(0);
    return now.toISOString();
}
