<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${empty param['algorithm'] or empty param['step']}">
		<h4 class="text-center">选择算法</h4>
		<form>
			<label class="radio">
  				<input type="radio" name="algorithm" value="direct" checked>
  				直接转发路由协议
			</label>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 1}">
		<h4 class="text-center">部署配置(区域配置)</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">区域长度</label>
				<div class="controls">
					<input type="text" name="length" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">区域宽度</label>
				<div class="controls">
					<input type="text" name="width" placeholder="double">
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 2}">
		<h4 class="text-center">部署配置(传感器配置)</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">部署类型</label>
				<div class="controls">
					<select name="deployType">
						<option value="0">随机部署</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">传感器数量</label>
				<div class="controls">
					<input type="text" name="nodeNum" placeholder="int">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">电池初始容量</label>
				<div class="controls">
					<input type="text" name="energy" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">传输范围</label>
				<div class="controls">
					<input type="text" name="transmissionRadius" placeholder="double">
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 3}">
		<h4 class="text-center">部署配置(汇集节点配置)</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">部署类型</label>
				<div class="controls">
					<select name="deployType">
						<option value="0">随机部署</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">汇集节点数量</label>
				<div class="controls">
					<input type="text" name="nodeNum" placeholder="int">
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 4}">
		<h4 class="text-center">部署配置(事件源配置)</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">部署类型</label>
				<div class="controls">
					<select name="deployType">
						<option value="0">随机部署</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">事件源半径</label>
				<div class="controls">
					<input type="text" name="radius" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">事件数量</label>
				<div class="controls">
					<input type="text" name="eventNum" placeholder="int">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">事件间隔</label>
				<div class="controls">
					<input type="text" name="eventInterval" placeholder="int">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">重复次数</label>
				<div class="controls">
					<input type="text" name="times" placeholder="int">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">事件数据量</label>
				<div class="controls">
					<input type="text" name="eventBit" placeholder="int">
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 5}">
		<h4 class="text-center">部署配置(能量消耗模型配置)</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">消耗模型</label>
				<div class="controls">
					<select name="consumeType">
						<option value="0">固定消耗</option>
						<option value="1">简单模型消耗</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">发射电路参数</label>
				<div class="controls">
					<input type="text" name="transmitter" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">放大器参数</label>
				<div class="controls">
					<input type="text" name="amplifier" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">接受电路参数</label>
				<div class="controls">
					<input type="text" name="receiver" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">放大指数</label>
				<div class="controls">
					<input type="text" name="exponent" placeholder="double">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">传感电路参数</label>
				<div class="controls">
					<input type="text" name="sensor" placeholder="double">
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${param['step'] eq 6}">
		<h4 class="text-center">命令参数</h4>
		<form>
			<label class="radio">
  				<input type="radio" name="output" value="0" checked>
  				简单结果输出
			</label>
			<label class="radio">
  				<input type="radio" name="output" value="1">
  				详细结果输出
			</label>
		</form>
	</c:when>
	<c:otherwise>
		无需配置了
	</c:otherwise>
</c:choose>