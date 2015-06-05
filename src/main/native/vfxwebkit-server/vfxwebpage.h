#ifndef VFXWEBPAGE_H
#define VFXWEBPAGE_H

#include <QWebPage>
#include <QPainter>

#include <vqt_shared_memory.hpp>

/**
 * @brief The VFXWebPage class renders HTML5 content to a shared memory buffer
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class VFXWebPage : public QWebPage
{
public:
    /**
     * @brief VFXWebPage constructor
     */
    VFXWebPage();
    /**
     * @brief ~VFXWebPage destructor
     */
    virtual ~VFXWebPage();
    /**
     * @brief pageBufferSize
     * @return page buffer size or \c -1 if no buffer is available
     */
    int pageBufferSize();
    /**
     * @brief pageBufferWidth
     * @return page buffer width or \c -1 if no buffer is available
     */
    int pageBufferWidth();
    /**
     * @brief pageBufferHeight
     * @return page buffer height or \c -1 if no buffer is available
     */
    int pageBufferHeight();
    /**
     * @brief setMinPageBufferSize
     * @param w image width (NOT bytes)
     * @param h image height (NOT bytes)
     */
    void setMinPageBufferSize(int w, int h);
    /**
     * @brief setSharedMem defines the shared memory info object to use for rendering
     * @param shm_info
     */
    void setSharedMem(vqt_shared_memory_info* shm_info);
    /**
     * @brief setSharedMemBuffer defines the shared memory buffer object to use for rendering
     * @param shm_buffer
     */
    void setSharedMemBuffer(uchar* shm_buffer);

protected:
    /**
     * @brief onRepaintEvent is called whenever the page needs to be updated
     * @param dirtyRect the dirty region that needs to be updated
     */
    void onRepaintEvent(const QRect & dirtyRect);

private:
    /**
     * @brief painter painter object used for rendering
     */
    QPainter*                    painter;
    /**
     * @brief image painting/graphics device used by painter object
     */
    QImage*                        image;
    /**
     * @brief shm_info shared memory info object
     */
    vqt_shared_memory_info*     shm_info;
    /**
     * @brief shm_buffer shared memory buffer object
     */
    uchar*                    shm_buffer;
};

#endif // VFXWEBPAGE_H
