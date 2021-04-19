# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)
file(GLOB_RECURSE GIF_LIB ${CMAKE_SOURCE_DIR}/giflib/*.c)
file(GLOB_RECURSE FRAME_SEQUENCE ${CMAKE_SOURCE_DIR}/*.cpp*)
# Declares and names the project.

project("gifoptimation")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library(gifoptimation
        SHARED
        ${FRAME_SEQUENCE}
        ${GIF_LIB})

list(APPEND LIBS
        jnigraphics
        android
        GLESv2
        log
        )

set(LIBS)
list(APPEND LIBS
        jnigraphics
        android
        GLESv2
        log
        )


target_link_libraries(gifoptimation ${LIBS})