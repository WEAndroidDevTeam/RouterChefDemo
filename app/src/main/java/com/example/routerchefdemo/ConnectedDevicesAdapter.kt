import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routerchefdemo.ConnectedDevice
import com.example.routerchefdemo.R
import com.example.routerchefdemo.databinding.ConnectedDevicesListItemBinding

class ConnectedDevicesAdapter() :
    RecyclerView.Adapter<ConnectedDevicesAdapter.DeviceViewHolder>() {
    private val deviceList: MutableList<ConnectedDevice> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ConnectedDevicesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = deviceList[position]
        holder.bind(device)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    fun submitList(devices: MutableList<ConnectedDevice>?) {
        clear()
        devices?.let {
            deviceList.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        deviceList.clear()
        notifyDataSetChanged()
    }

    inner class DeviceViewHolder(private val binding: ConnectedDevicesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(device: ConnectedDevice) {
            binding.tVDeviceName.text = device.hostName

            if(device.IconType == "\"\"")
                binding.ivDeviceIcon.setImageResource(R.drawable.monitor_9561573)
            else
                binding.ivDeviceIcon.setImageResource(R.drawable.smartphone_1434518)
        }
    }
}