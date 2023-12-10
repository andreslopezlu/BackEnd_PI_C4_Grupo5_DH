FROM openjdk:11

# Establecer directorio de trabajo
VOLUME [ "/tmp" ]

# Exponer puerto
EXPOSE 8080

# Copiar jar al contenedor
COPY ./target/AlquilerEquiposConstruccion-0.0.1-SNAPSHOT.jar ./

# Run container
ENTRYPOINT ["java","-jar","/AlquilerEquiposConstruccion-0.0.1-SNAPSHOT.jar"]