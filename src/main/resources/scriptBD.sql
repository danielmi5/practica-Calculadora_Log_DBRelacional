
CREATE TABLE IF NOT EXISTS ERROR (
                                     fecha TEXT NOT NULL,
                                     hora TEXT NOT NULL,
                                     msjError TEXT NOT NULL,
                                     PRIMARY KEY (fecha, hora)
    );


CREATE TABLE IF NOT EXISTS OPERACION (
                                         fecha TEXT NOT NULL,
                                         hora TEXT NOT NULL,
                                         num1 REAL NOT NULL,
                                         operador TEXT NOT NULL,
                                         num2 REAL NOT NULL,
                                         resultado REAL NOT NULL,
                                         PRIMARY KEY (fecha, hora)
    );