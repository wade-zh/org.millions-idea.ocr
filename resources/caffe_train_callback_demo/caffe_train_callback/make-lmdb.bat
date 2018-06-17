@echo off

title 如果是第一次执行，请先解压yzm-easy.rar然后再执行

set tips_info=0
if exist train_lmdb set tips_info=1
if exist val_lmdb set tips_info=1

if %tips_info% == 1 (
    echo 确定要创建吗，这个操作会删掉已有目录的？，防止误操作把好不容易创建的删除掉啦
    pause
)


if exist train_lmdb rd /q /s train_lmdb
if exist val_lmdb rd /q /s val_lmdb

rem 如果要设计图像尺寸的，比如，可以加参数--resize_width=224 --resize_height=224
rem 如果要讲输入图像弄成单通道灰度图，可以加参数--gray=true
rem 可以指定为--db_mode=append或者--db_mode=new，表示添加数据和创建新数据库

echo 创建训练数据库train_lmdb...
"D:/caffe_train_components/bin-cpu/convert_imageset.exe" "" label-train.txt train_lmdb --shuffle=true --resize_width=120 --resize_height=30
echo 创建验证数据库train_lmdb...
"D:/caffe_train_components/bin-cpu/convert_imageset.exe" "" label-val.txt val_lmdb --shuffle=true  --resize_width=120 --resize_height=30
echo LMDB数据库创建完毕。
pause