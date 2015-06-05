#include "vfxwebpage.h"

#include <QApplication>
#include <QWebFrame>
#include <QRect>
#include <QPixelFormat>

#include <iostream>

VFXWebPage::VFXWebPage(): painter(NULL), image(NULL)
{
    QWebSettings::globalSettings()->setAttribute(QWebSettings::WebGLEnabled, true);

    setViewportSize(QSize(1024,768));

    connect(this, &VFXWebPage::repaintRequested, this, &VFXWebPage::onRepaintEvent);

    mainFrame()->setScrollBarPolicy(Qt::Horizontal, Qt::ScrollBarAlwaysOff);
    mainFrame()->setScrollBarPolicy(Qt::Vertical, Qt::ScrollBarAlwaysOff);

    mainFrame()->load(QUrl("http://carvisualizer.plus360degrees.com/threejs/"));
}

int VFXWebPage::pageBufferSize() {
    return image->byteCount();
}

int VFXWebPage::pageBufferWidth() {
    return image->bytesPerLine();
}

int VFXWebPage::pageBufferHeight() {
    return image->height()*image->pixelFormat().bitsPerPixel()/8;
}

void VFXWebPage::onRepaintEvent(const QRect &dirtyRect) {

    if (image == NULL || painter == NULL) return;

    shm_info->mutex.lock();

//    if (shm_info->new_url) {
//        mainFrame()->load(QUrl(shm_info->url));
//        shm_info->new_url = false;
//    }

    // create new buffer
    if (image->width()!=shm_info->w||image->height()!=shm_info->h) {
        //setMinPageBufferSize(shm_info->w,shm_info->h);
    }

    painter->begin(image);
    this->mainFrame()->render(painter, dirtyRect);
    painter->end();

    shm_info->dirty = true;

    shm_info->mutex.unlock();
}

void VFXWebPage::setMinPageBufferSize(int w, int h) {
    std::cout << "NATIVE: set-min-size: " << w << ", " << h << std::endl;
//    image = new QImage(QSize(w,h), QImage::Format_ARGB32_Premultiplied);
//    painter = new QPainter(image);
    onRepaintEvent(QRect(0,0,w,h));
}

void VFXWebPage::setSharedMem(vqt_shared_memory_info* shm_info) {
    this->shm_info = shm_info;
}

void VFXWebPage::setSharedMemBuffer(uchar* shm_buffer) {
//    std::cout << "NATIVE: set shared-mem-buffer" << std::endl;
    shm_info->mutex.lock();
    this->shm_buffer = shm_buffer;
    image = new QImage(this->shm_buffer, 1024,768, 1024*4, QImage::Format_ARGB32_Premultiplied);
    shm_info->mutex.unlock();
    //image = new QImage(800,600, QImage::Format_ARGB32_Premultiplied);

//    std::cout << "NATIVE: a new img: " << image << std::endl;

    painter = new QPainter();
}

VFXWebPage::~VFXWebPage() {
    delete image;
    delete painter;
}
