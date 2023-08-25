const correctAnswers = [
    "Perfekt, arbeiten mit Profis. Das wird schwer für mich.",
    "Keine Frage, das ist richtig.",
    "Genau richtig, das ist die korrekte Antwort.",
    "Korrekt, ihr habt den Nagel auf den Kopf getroffen.",
    "Bravo, besser geht es nicht.",
    "Ausgezeichnet, ihr habt die richtige Antwort begeben."
];

const wrongAnswers = [
    "Kennt ihr den Satz mit X? Das war wohl nix! Versucht es noch mal.",
    "Das war nicht richtig, aber gebt nicht auf.",
    "Netter Versuch, aber das stimmt nicht. Aufgeben ist keine Option, macht weiter.",
    "Hmm, das stimmt leider nicht, probiert es noch mal.",
    "Schade das ist nicht korrekt, versucht es erneut.",
    "Das ist leider nicht die richtige Antwort, bei nächsten mal findet ihr die Lösung, einfach weiter machen."
];

const generate = (answers) => {
    return answers[Math.floor(Math.random() * answers.length)];
}

export function generateCorrectAnswer() {
    return generate(correctAnswers);
}

export function generateWrongAnswer() {
    return generate(wrongAnswers);
}