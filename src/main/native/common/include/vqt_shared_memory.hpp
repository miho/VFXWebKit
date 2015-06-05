#include <boost/interprocess/mapped_region.hpp>
#include <boost/interprocess/shared_memory_object.hpp>
#include <boost/interprocess/sync/interprocess_mutex.hpp>
#include <boost/interprocess/containers/string.hpp>

#ifndef VQT_SHARED_MEMORY_HPP
#define VQT_SHARED_MEMORY_HPP



#include <boost/interprocess/allocators/allocator.hpp>
#include <boost/interprocess/offset_ptr.hpp>


#include <string>

typedef boost::interprocess::basic_string<char> shared_string;

struct vqt_shared_memory_info
{

   vqt_shared_memory_info()
      : img_buffer_size(0),
        w(0), h(0),
        dirty(true), new_url(true)
   {}

   //Mutex to protect access
   boost::interprocess::interprocess_mutex mutex;

   int img_buffer_size;
   
   int w;
   int h;

   bool dirty;

   bool new_url;
   
   shared_string url;
   
};


struct vqt_shared_memory_buffer
{

};

#endif // VQT_SHARED_MEMORY_HPP
