# Marvel Heroes 💪🏻

Marvel Heroes es una app para Android que lista algunos de los súperheroes de Marvel con sus características.

## Mejoras realizadas:

Notas sobre la versión modificada:

- Refactorización para usar MVVM Clean con ViewModel y LiveData de JetPack.

- Persistencia de los datos de la API en una caché usando Room.

- Implementación de un sistema básico para marcar héroes como favoritos. La marca de favorito se persiste localmente en Room mediante una entidad para atributos extendidos que no proporciona la API. Se puede utilizar a nivel de la vista de detalle, tocando la estrella y, como alternativa, por medio de una opción de menú.

- No he sido capaz de hacer funcionar Dagger2 para inyectar dependencias en ViewModel. En su lugar, de momento utilizo una clase Injector como _service locator_ para inyectar el repositorio.

- Dejo pendiente mostrar la marca de favorito también en la lista de héroes.


 Main                      |  Detail
:-------------------------:|:-------------------------:
![main](https://raw.githubusercontent.com/jerosanchez/MarvelHeroes/master/art/main.png) | ![detail](https://raw.githubusercontent.com/jerosanchez/MarvelHeroes/master/art/detail.png)

## Características ✨

- Kotlin
- MVP Clean (simplificado)
- Dagger2
- RxJava2

## Notas 📝

- Utiliza una arquitectura MVP Clean (simplificada). Se ha simplicado dicha arquitectura con motivo de demostrar el uso de una arquitectura limpia con batería de tests sin perder demasiado tiempo teniendo en cuenta que es una app demo.

- Se ha utilizado ActionBar en lugar de implementar Toolbar con el objetivo de ahorrar tiempo.

- He utilizado `notifyDataSetChanged()` por el mismo motivo que anteriormente: el tiempo. Sin embargo, lo recomendado sería utilizar [DiffUtil](https://developer.android.com/reference/android/support/v7/util/DiffUtil.html), el cual nos calcula la diferencia entre dos listas para refrescar la información con menos consumo de recursos y animando el resultado.

- Normalmente suelo aplicar BDD mediante [GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html), sin embargo, este no ha sido el caso (en todos los tests, al menos). Sí, una vez más por el mismo motivo 😛

- Se ha utilizado [Android KTX](https://github.com/android/android-ktx) para añadir ciertas extension functions muy interesantes y útiles para el desarrollo. Algunas como `Context.toast()` no están en el repositorio de Maven en el momento que se desarrolla esta app; aunque sí que lo está en el repositorio de GitHub.

- Con más tiempo me hubiera gustado implementar [Room](https://developer.android.com/topic/libraries/architecture/room.html) o cualquier otro DAO/ORM para mostrar un ejemplo claro de Repository.

- Podría y debería haber más tests
