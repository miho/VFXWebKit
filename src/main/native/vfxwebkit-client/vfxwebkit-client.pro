#-------------------------------------------------
#
# Project created by QtCreator 2015-05-20T20:45:02
#
#-------------------------------------------------

QT       -= core webkit webkitwidgets

TARGET = vfxwebkit
TEMPLATE = lib

QMAKE_CXXFLAGS += -std=c++11
CONFIG += c++11
CONFIG += release

SOURCES += vfxwebkitapi.cpp \
    jnitypeconverter.cpp

HEADERS  += \
    	vfxwebkitapi.h \
    vfxbindings.h \
    jnitypeconverter.h \
    threading.h
	
# COMMON
INCLUDEPATH += ../common/include/

# BOOST
INCLUDEPATH += ../ext/headers/

message(Using JDK: $${JDK_HOME})

LIBS += -L$$JDK_HOME/jre/lib/server
LIBS += -ljvm

INCLUDEPATH += $$JDK_HOME/include
INCLUDEPATH += $$JDK_HOME/include/linux
INCLUDEPATH += $$JDK_HOME/include/darwin
INCLUDEPATH += $$JDK_HOME/include/win32

DESTDIR = dist

# release:DESTDIR = dist/release
# release:OBJECTS_DIR = dist/release/.obj
# release:MOC_DIR = dist/release/.moc
# release:RCC_DIR = dist/release/.rcc
# release:UI_DIR = dist/release/.ui

# debug:DESTDIR = dist/debug
# debug:OBJECTS_DIR = dist/debug/.obj
# debug:MOC_DIR = dist/debug/.moc
# debug:RCC_DIR = dist/debug/.rcc
# debug:UI_DIR = dist/debug/.ui
