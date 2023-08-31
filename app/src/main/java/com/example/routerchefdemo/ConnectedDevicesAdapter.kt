import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routerchefdemo.ConnectedDevice
import com.example.routerchefdemo.R
import com.example.routerchefdemo.databinding.ConnectedDevicesListItemBinding

class ConnectedDevicesAdapter(private val deviceList: List<ConnectedDevice>) :
    RecyclerView.Adapter<ConnectedDevicesAdapter.DeviceViewHolder>() {

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

    inner class DeviceViewHolder(private val binding: ConnectedDevicesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(device: ConnectedDevice) {
            binding.tVDeviceName.text = device.hostName
        }
    }
}