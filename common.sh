#!/usr/bin/env bash

#set -x

# $1 project path
# $2 lib-project path
function update_lib_project() {
   local project=$1
   local lib_project=$2
   
   if [ "x$ANDROID_TARGET" == "x" ] ; then
      echo "no ANDROID_TARGET exported."
#      exit 1
   fi

   android update lib-project -p $project -t $ANDROID_TARGET -l $lib_project
   set_as_lib_project $lib_project
} 

# $1 lib-project path
# $2 true of fasle
function set_as_lib_project() {
   local p_file=$1/project.properties
   local lib=$2
   if [ "x$lib" == "x" ] ; then
      lib=true
   fi
   if file_content_match $p_file "[ \t]*android\.library=.*"; then
      local p_bak=${p_file}.bak
      mv $p_file $p_bak
      sed   -e "/^[ \t]*android\.library[ \t]*=[ \t]*.*/ s/^[ \t]*\(android\.library\)[ \t]*=[ \t]*.*/\1=$lib/ " $p_bak > $p_file
   else 
      echo "android.library=$lib" >> $p_file
   fi
}

# $1 file
# $2 regrex
function file_content_match() {
   grep "$2" "$1" 
   return $?
}

#set_as_lib_project .
