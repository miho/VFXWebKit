#-------------------------------------------------
#
# Project created by QtCreator 2015-05-20T20:45:02
#
#-------------------------------------------------

QMAKE_MAC_SDK = macosx10.11

QT       += core webkit webkitwidgets

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = vfxwebkit-server
TEMPLATE = app

QMAKE_CXXFLAGS += -std=c++11
CONFIG += c++11
CONFIG += release

SOURCES += \
    	vfxwebpage.cpp \
    main.cpp

HEADERS  += \
    	vfxwebpage.h
	
# COMMON
INCLUDEPATH += ../common/include/

# BOOST
INCLUDEPATH += ../ext/headers/

DESTDIR = dist/

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
