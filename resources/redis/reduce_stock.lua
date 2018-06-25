--[[
	本脚本用于高并发下扣减用户余额(库存)
	@author wadezh
	@date 2018年6月25日16:43:17

	扣减规则：
	1、首先通过用户登录时存入redis的key查询到用户信息
	2、解析用户信息，找到wallet对象中的balance字段
	3、进行原子性操作，根据用户指定的扣减基数进行计算
	4、保存到redis服务器中，如果操作失败应返回对应的错误编码给上游系统
	
	返回编码：
	-1000 userKey不存在
	-1001 余额不足
	
--]]
local userKey = KEYS[1]
local num = tonumber(ARGV[1])


if redis.call("exists", userKey) == 1 then
	local stock = tonumber(redis.call("get", userKey))
	if stock <= 0 then return -1001 end
	if stock >= num then return redis.call('incrbyfloat', userKey, 0 - num) end
else
	return -1000
end